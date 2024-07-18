'use client';

import { ThemeProvider as MuiThemeProvider } from '@mui/material/styles';
import { darkTheme, lightTheme, ThemeMode } from '@/config';
import { useEffect } from 'react';
import { CssBaseline } from '@mui/material';
import { useThemeStore } from '@/stores';

interface ThemeProviderProps {
  children: React.ReactNode;
}

const ThemeProvider = ({ children }: ThemeProviderProps) => {
  const { themeMode, toggleTheme } = useThemeStore();

  useEffect(() => {
    document.body.classList.toggle('dark', themeMode === ThemeMode.DARK);
  }, [themeMode]);

  const theme = themeMode === ThemeMode.LIGHT ? lightTheme : darkTheme;

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
