import { ThemeMode } from '@/config';
import { THEME_KEY } from '@/themes';
import { create } from 'zustand';
import { persist } from 'zustand/middleware';

interface IThemeStore {
  themeMode: ThemeMode;
  toggleTheme: () => void;
}

export const useThemeStore = create<IThemeStore>(
  persist(
    (set) => ({
      themeMode: DEFAULT_MODE,
      toggleTheme: () =>
        set((state) => ({
          themeMode:
            state.themeMode === ThemeMode.LIGHT
              ? ThemeMode.DARK
              : ThemeMode.LIGHT
        }))
    }),
    {
      name: THEME_KEY
    }
  )
);
