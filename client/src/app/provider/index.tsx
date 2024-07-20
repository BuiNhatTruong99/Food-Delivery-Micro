'use client';

import { SnackbarUtilsConfiguration } from '@/hooks/useMessageRef';
import ThemeProvider from '@/themes';
import { SnackbarProvider } from 'notistack';

export default function Providers({ children }: { children: React.ReactNode }) {
  return (
    <SnackbarProvider preventDuplicate maxSnack={1}>
      <ThemeProvider>{children}</ThemeProvider>
      <SnackbarUtilsConfiguration />
    </SnackbarProvider>
  );
}
