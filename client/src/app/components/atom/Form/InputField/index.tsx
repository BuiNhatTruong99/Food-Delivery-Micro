import {
  FieldValues,
  Path,
  Controller,
  Control,
  FieldError
} from 'react-hook-form';
import { Box, SxProps, Theme, Typography } from '@mui/material';
import { ReactNode, useMemo } from 'react';
import Input, { IInputProps } from '../../Input';

export interface InputFieldProps<Type extends FieldValues>
  extends Omit<IInputProps, 'label'> {
  control: Control<Type>;
  name: Path<Type>;
  wrapperSx?: SxProps<Theme>;
  layoutType?: 'vertical' | 'horizontal';
  errorField?: FieldError;
  label?: ReactNode | string;
}

const InputField = <T extends FieldValues>(props: InputFieldProps<T>) => {
  const { control, name, label, layoutType, errorField, wrapperSx, ...rest } =
    props;

  const isErrorField = useMemo(
    () => errorField?.message && errorField?.message?.length > 0,
    [errorField]
  );
  return (
    <Box
      sx={{
        display: 'flex',
        flexDirection: layoutType === 'vertical' ? 'row' : 'column',
        gap: '4px',
        position: 'relative',
        height: '105px',
        ...wrapperSx
      }}
    >
      <Typography
        variant="body1"
        className={`mb-[6px] font-medium  text-[#9796A1]`}
      >
        {label}
      </Typography>
      <Controller
        control={control}
        name={name}
        defaultValue={'' as any}
        render={({ field }) => (
          <Input
            wrapperProps={{
              sx: {
                height: '55px',
                border: `1px solid  ${!isErrorField ? '#EEEEEE' : '#dc3545'}`,
                borderRadius: '10px',
                padding: '14px 12px'
              }
            }}
            id={name}
            {...rest}
            {...field}
          />
        )}
      />
      {isErrorField && (
        <Typography
          variant="body2"
          className="text-primary absolute top-[25px] z-10  p-[2px] left-3 text-[13px] bg-white dark:bg-darkPaper"
        >
          {errorField?.message}
        </Typography>
      )}
    </Box>
  );
};

export default InputField;
