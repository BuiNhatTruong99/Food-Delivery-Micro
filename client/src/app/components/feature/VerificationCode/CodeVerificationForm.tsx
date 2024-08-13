'use client';

import React, {
  useState,
  useRef,
  ChangeEvent,
  KeyboardEvent,
  ClipboardEvent,
  useEffect,
  useCallback
} from 'react';
import { Box, ButtonBase, TextField, Typography } from '@mui/material';
import { Button, Form } from '@/components/atom';
import { useRouter } from 'next/navigation';
import { useMessage } from '@/hooks/useMessage';
import { useSendOtpMutation, useVerificationEmailMutation } from '@/queries';
import { IApiErrorResponse, ISendOtp, IVerificationEmail } from '@/domain';
import { useAuthStore } from '@/stores';
import { PATHNAME } from '@/config';

const CodeVerificationForm: React.FC = () => {
  const [values, setValues] = useState<string[]>(['', '', '', '']);
  const inputs = useRef<(HTMLInputElement | null)[]>([]);
  const [resendCountdown, setResendCountdown] = useState<number>(0);
  const timer = useRef<ReturnType<typeof setInterval> | null>(null);

  const { push } = useRouter();
  const { mutateAsync } = useSendOtpMutation();
  const { mutateAsync: mutateVerificationEmail } =
    useVerificationEmailMutation();
  const { userInfo, setUserInfo } = useAuthStore();
  const message = useMessage();

  const handleChange = (e: ChangeEvent<HTMLInputElement>, index: number) => {
    const val = e.target.value;
    if (/^[0-9]$/.test(val) || val === '') {
      const newValues = [...values];
      newValues[index] = val;
      setValues(newValues);

      if (val !== '' && index < 3) {
        inputs.current[index + 1]?.focus();
      }
    }
  };

  const handleKeyDown = (e: KeyboardEvent<HTMLInputElement>, index: number) => {
    if (e.key === 'Backspace' && !values[index] && index > 0) {
      inputs.current[index - 1]?.focus();
    }
  };

  const handlePaste = (e: ClipboardEvent<HTMLDivElement>) => {
    const pasteData = e.clipboardData.getData('text');
    if (/^\d{4}$/.test(pasteData)) {
      const newValues = pasteData.split('');
      setValues(newValues);
      inputs.current[3]?.focus();
    }
  };

  const handleResend = useCallback(() => {
    const email = userInfo?.email;
    if (!email) {
      message.error('Email not found');
      return;
    }

    const value: ISendOtp = { email };

    mutateAsync(value, {
      onSuccess: (res) => {
        if (res) {
          message.success('Please check your email');
          if (resendCountdown === 0) {
            setResendCountdown(60);
            timer.current = setInterval(() => {
              setResendCountdown((prev) => {
                if (prev === 1 && timer.current) {
                  clearInterval(timer.current);
                  timer.current = null;
                }
                return prev - 1;
              });
            }, 1000);
          }
        }
      },
      onError: (err: IApiErrorResponse) => {
        message.error(err?.message);
      }
    });
  }, [message, mutateAsync, resendCountdown, userInfo?.email]);

  useEffect(() => {
    return () => {
      if (timer.current) {
        clearInterval(timer.current);
      }
    };
  }, []);

  const onSubmit = useCallback(
    (e: React.FormEvent) => {
      e.preventDefault();

      const userId = userInfo?.id;
      const otp = parseInt(values.join(''));
      const value: IVerificationEmail = { userId, otp };

      mutateVerificationEmail(value, {
        onSuccess: (res) => {
          if (res && res?.data) {
            const {
              id,
              email,
              isEmailVerified,
              role,
              fullName,
              addresses,
              imageUrl
            } = res.data;
            setUserInfo({
              id,
              email,
              isEmailVerified,
              role,
              fullName,
              imageUrl,
              addresses
            });
            push(PATHNAME.HOME);
          }
        },
        onError: (err: IApiErrorResponse) => {
          message.error(err?.message);
        }
      });
    },
    [message, mutateVerificationEmail, setUserInfo, userInfo, values, push]
  );

  return (
    <Form onSubmit={onSubmit} className="flex flex-col gap-10">
      <Typography
        variant="body2"
        className="w-[247px] h-[40px] text-grayLight dark:text-grayDark"
      >
        Please type the verification code sent to {''}
        {userInfo ? userInfo.email : 'your email'}
      </Typography>
      <Box className="flex justify-between" onPaste={handlePaste}>
        {values.map((value, index) => (
          <TextField
            key={index}
            value={value}
            onChange={(e) =>
              handleChange(e as ChangeEvent<HTMLInputElement>, index)
            }
            onKeyDown={(e) =>
              handleKeyDown(e as KeyboardEvent<HTMLInputElement>, index)
            }
            inputRef={(el) => (inputs.current[index] = el)}
            sx={{ width: 65, height: 65 }}
            inputProps={{
              maxLength: 1,
              style: {
                height: '100%',
                textAlign: 'center',
                fontSize: '27px',
                color: '#FE724C',
                fontWeight: 700
              }
            }}
            variant="outlined"
          />
        ))}
      </Box>

      <Box className="flex flex-col gap-10 text-center">
        <Typography variant="body2" className="flex justify-center gap-2">
          {resendCountdown > 0
            ? `Please check your email !`
            : `I don’t receive a code!`}{' '}
          <ButtonBase
            className="text-primary"
            onClick={handleResend}
            disabled={resendCountdown > 0}
          >
            {resendCountdown > 0
              ? `Please wait ${resendCountdown}s`
              : 'Please resend'}
          </ButtonBase>
        </Typography>
        <Button
          size="large"
          htmlType="submit"
          type="primary"
          className="text-primary"
          disabled={values.some((value) => value === '')}
        >
          Verify
        </Button>
      </Box>
    </Form>
  );
};

export default CodeVerificationForm;
