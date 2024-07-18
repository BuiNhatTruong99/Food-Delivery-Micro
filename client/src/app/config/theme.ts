'use client';

import { createTheme, PaletteOptions } from '@mui/material';
import { Sofia_Sans } from 'next/font/google';

const breakpoints = {
  values: { xs: 0, sm: 768, md: 900, lg: 1200, xl: 1536 }
};

export const Sofia = Sofia_Sans({
  weight: ['400', '500', '600', '700'],
  subsets: ['latin'],
  display: 'swap',
  fallback: ['Arial', 'sans-serif']
});

const typography = {
  fontFamily: Sofia.style.fontFamily,
  h1: {
    fontWeight: 700,
    fontSize: 40,
    lineHeight: 1.25
  },
  h2: {
    fontWeight: 700,
    fontSize: 32,
    lineHeight: 1.1875
  },
  h3: {
    fontWeight: 700,
    fontSize: 24,
    lineHeight: 1.25
  },
  h4: {
    fontWeight: 600,
    fontSize: 20,
    lineHeight: 1.2
  },
  h5: {
    fontWeight: 600,
    fontSize: 16,
    lineHeight: 1.25
  },
  h6: {
    fontSize: 14,
    lineHeight: '18px',
    fontWeight: 600
  },
  body1: {
    fontSize: 16
  },
  body2: {
    fontSize: 14
  },
  subtitle1: {
    fontSize: 20
  },
  subtitle2: {
    fontSize: 17
  },
  caption: {
    fontSize: 12
  },
  overline: {
    fontSize: 18
  }
};

export default typography;

export enum ThemeMode {
  LIGHT = 'light',
  DARK = 'dark'
}

export const DEFAULT_MODE = ThemeMode.LIGHT;

// Create a theme.
const lightPalette: PaletteOptions = {
  mode: ThemeMode.LIGHT,
  common: {
    black: '#000000',
    white: '#FFFFFF'
  },
  primary: {
    main: '#FE724C'
  },
  text: {
    primary: '#212121'
  },
  background: {
    paper: '#FFFFFF',
    default: '#FFFFFF'
  }
};

const darkPalette: PaletteOptions = {
  mode: ThemeMode.DARK,
  common: {
    black: '#000000',
    white: '#FFFFFF'
  },
  primary: {
    main: '#FE724C'
  },
  text: {
    primary: '#FFFFFF'
  },
  background: {
    paper: '#2D2D3A',
    default: '#2D2D3A'
  }
};

export const lightTheme = createTheme({
  breakpoints,
  typography,
  palette: lightPalette
});

export const darkTheme = createTheme({
  breakpoints,
  typography,
  palette: darkPalette
});

export const THEMES_CONSTANT = {
  breakpoints,
  typography,
  lightTheme,
  darkTheme
};
