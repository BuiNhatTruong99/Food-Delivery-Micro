'use client';

import { ThemeProvider as MuiThemeProvider } from '@mui/material/styles';
import { darkTheme, lightTheme } from '@/config';
import { useState } from 'react';
import { CssBaseline } from '@mui/material';

interface ThemeProviderProps {
  children: React.ReactNode;
}

const ThemeProvider = ({ children }: ThemeProviderProps) => {
  const [themeMode, setThemeMode] = useState<'light' | 'dark'>('dark');
  const toggleTheme = () => {
    setThemeMode((prevMode) => (prevMode === 'light' ? 'dark' : 'light'));
  };

  const theme = themeMode === 'light' ? lightTheme : darkTheme;

  return (
    <MuiThemeProvider theme={theme}>
      <CssBaseline />
      {children}
      <button
        onClick={toggleTheme}
        className="fixed bottom-4 right-4 p-2 bg-gray-800 text-white rounded"
      >
        Toggle Theme
      </button>
    </MuiThemeProvider>
  );
};

export default ThemeProvider;
