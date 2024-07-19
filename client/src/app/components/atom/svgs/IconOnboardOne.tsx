import { SvgIcon, SvgIconProps } from '@mui/material';
import { memo } from 'react';

const IconSlideOne = (props: SvgIconProps) => {
  return (
    <SvgIcon
      width="226"
      height="302"
      viewBox="0 0 226 302"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
      {...props}
    >
      <rect
        x="-103"
        y="-31"
        width="423"
        height="553"
        fill="url(#pattern0_427_591)"
      />
      <defs>
        <pattern
          id="pattern0_427_591"
          patternContentUnits="objectBoundingBox"
          width="1"
          height="1"
        >
          <use transform="matrix(0.00229358 0 0 0.0017544 0 -0.00175855)" />
        </pattern>
        <image id="image0_427_591" width="436" height="572" />
      </defs>
    </SvgIcon>
  );
};

export default memo(IconSlideOne);
