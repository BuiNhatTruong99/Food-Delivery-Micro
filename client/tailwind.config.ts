module.exports = {
  mode: 'jit',
  darkMode: 'class',
  content: [
    './src/pages/**/*.{js,ts,jsx,tsx,mdx}',
    './src/components/**/*.{js,ts,jsx,tsx,mdx}',
    './src/app/**/*.{js,ts,jsx,tsx,mdx}'
  ],
  theme: {
    extend: {
      colors: {
        primary: '#FE724C',
        darkPaper: '#2D2D3A',
        darkDefault: '#3B3A39',
        grayDark: '#C9CCD1',
        grayLight: '#616772'
      },
      boxShadow: {
        socialBtn:
          '18.58696px 18.58696px 37.17392px 0px rgba(211, 209, 216, 0.5)',
        primaryBtn: '0px 10px 20px 0px rgba(254, 114, 76, 0.50)',
        welcomePage: 'inset 0px -120px 150px rgba(37, 39, 58, 0.8);'
      },
      animation: {
        scale: 'scaleUp 1.25s ease-in-out infinite',
        shiny: 'shiny 1.5s ease-in-out infinite'
      },
      keyframes: {
        scaleUp: {
          '0%, 100%': { transform: 'scale(1)' },
          '50%': { transform: 'scale(1.25)' }
        },
        shiny: {
          '0%': { transform: 'scale(0) rotate(45deg)', opacity: 0 },
          '80%': { transform: 'scale(0) rotate(45deg)', opacity: 0.5 },
          '81%': { transform: 'scale(4) rotate(45deg)', opacity: 1 },
          '100%': { transform: 'scale(50) rotate(45deg)', opacity: 0 }
        }
      }
    }
  },
  plugins: []
};
