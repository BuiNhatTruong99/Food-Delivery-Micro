import { Theme } from '@emotion/react';
import {
  Box,
  ButtonBase,
  ButtonBaseProps,
  CircularProgress,
  SxProps
} from '@mui/material';
import { FC } from 'react';

export enum TypeButton {
  'primary' = 'primary',
  'default' = 'default',
  'danger' = 'danger'
}

export enum SizeButton {
  'large' = 'large',
  'medium' = 'medium',
  'small' = 'small'
}

const backgroundByType = {
  [TypeButton.primary]: '#FE724C',
  [TypeButton.danger]: '#ff0e0e',
  [TypeButton.default]: '#F6F6F6'
};

const colorByType = {
  [TypeButton.primary]: 'white',
  [TypeButton.danger]: 'white',
  [TypeButton.default]: '#515154'
};

const borderByType = {
  [TypeButton.primary]: '1px solid #FE724C',
  [TypeButton.danger]: '1px solid #ff0e0e',
  [TypeButton.default]: '1px solid #333333'
};

const heightBySize = {
  [SizeButton.large]: '60px',
  [SizeButton.medium]: '48px',
  [SizeButton.small]: '32px'
};

const widthBySize = {
  [SizeButton.large]: '248px',
  [SizeButton.medium]: '160px',
  [SizeButton.small]: '124px'
};

const fontSizeBySize = {
  [SizeButton.large]: '18px',
  [SizeButton.medium]: '15px',
  [SizeButton.small]: '14px'
};

interface IButton extends Omit<ButtonBaseProps, 'type'> {
  wrapperSx?: SxProps<Theme>;
  htmlType: 'button' | 'submit' | 'reset';
  type: keyof typeof TypeButton;
  size: keyof typeof SizeButton;
  loading?: boolean;
}

const Button: FC<IButton> = ({
  htmlType,
  type,
  wrapperSx,
  sx,
  loading,
  ...props
}) => {
  return (
    <Box sx={wrapperSx}>
      <ButtonBase
        style={{
          ...(type
            ? {
                color: colorByType[type],
                border: borderByType[type],
                backgroundColor: backgroundByType[type],
                opacity: props?.disabled ? 0.6 : 1
              }
            : {})
        }}
        sx={{
          height: heightBySize[props.size],
          width: widthBySize[props.size],
          fontSize: fontSizeBySize[props.size],
          fontWeight: 600,
          borderRadius: '29px',
          ...sx
        }}
        type={htmlType}
        {...props}
      >
        {loading ? (
          <CircularProgress
            size="1rem"
            sx={{
              color: 'white'
            }}
          />
        ) : (
          props.children
        )}
      </ButtonBase>
    </Box>
  );
};

export default Button;
