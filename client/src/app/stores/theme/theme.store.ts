import { DEFAULT_MODE, ThemeMode } from '@/config';
import { create } from 'zustand';
import { persist } from 'zustand/middleware';

export const THEME_KEY = 'food_hub_mode';

interface IThemeStore {
  themeMode: ThemeMode;
  toggleTheme: () => void;
}

export const useThemeStore = create<IThemeStore>()(
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
