import { Box, BoxProps, InputBase, InputBaseProps } from '@mui/material';
import React, { FC } from 'react';

export interface IInputProps extends InputBaseProps {
  wrapperProps?: BoxProps;
}

const Input: FC<IInputProps> = React.forwardRef(
  ({ wrapperProps, ...props }, ref) => {
    return (
      <Box {...wrapperProps}>
        <InputBase ref={ref} {...props} className="h-[1rem]" />
      </Box>
    );
  }
);

Input.displayName = 'Input';

export default Input;
