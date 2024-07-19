'use client';

import React, {
  useState,
  useRef,
  ChangeEvent,
  KeyboardEvent,
  ClipboardEvent
} from 'react';
import { Box, ButtonBase, TextField, Typography } from '@mui/material';
import { Button, Form } from '@/components/atom';

const CodeVerificationForm: React.FC = () => {
  const [values, setValues] = useState<string[]>(['', '', '', '']);
  const inputs = useRef<(HTMLInputElement | null)[]>([]);

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

  return (
    <Form className="flex flex-col gap-10">
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
        <Typography variant="body2">
          I don’t receive a code!{' '}
          <ButtonBase className="text-primary">Please resend</ButtonBase>
        </Typography>
        <Button
          size="large"
          htmlType="submit"
          type="primary"
          className="text-primary"
        >
          Verify
        </Button>
      </Box>
    </Form>
  );
};

export default CodeVerificationForm;
