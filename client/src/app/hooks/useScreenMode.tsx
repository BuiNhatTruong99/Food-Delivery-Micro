import useWindowSize from './useWindowSize';

const useScreenMode = () => {
  const { width = 0, height = 0 } = useWindowSize();

  return {
    isMobile: width < 600,
    isIpad: width >= 600 && width <= 1024,
    isDesktop: width > 1024,
    isExtraDesktop: width > 1600,
    isSuperDesktop: width >= 1920,

    size: {
      width,
      height
    }
  };
};

export default useScreenMode;
