import { PageTransition } from '@/components/molecule';
import { IMAGES_DEFAULT } from '@/constant';
import { Box, Typography } from '@mui/material';
import Image from 'next/image';
import { Swiper, SwiperSlide, SwiperRef } from 'swiper/react';
import { Navigation, Pagination } from 'swiper/modules';
import 'swiper/css';
import { Button } from '@/components/atom';
import { useRef } from 'react';
import { StepKey, useOnboarding } from '../useOnBoaring';

const slides = [
  {
    id: 1,
    step: 'step2',
    title: 'Browse your menu and order directly',
    description:
      'Our app can send you everywhere, even space. For only $2.99 per month',
    image: IMAGES_DEFAULT.onboarding.onboard1
  },
  {
    id: 2,
    step: 'step3',
    title: 'Even to space with us! Together',
    description:
      'Our app can send you everywhere, even space. For only $2.99 per month',
    image: IMAGES_DEFAULT.onboarding.onboard2
  },
  {
    id: 3,
    step: 'done',
    title: 'Pick delivery at your door',
    description:
      'Our app can send you everywhere, even space. For only $2.99 per month',
    image: IMAGES_DEFAULT.onboarding.onboard3
  }
];

const SliderOnboarding = () => {
  const { onNextStep } = useOnboarding();
  const swiperRef = useRef<SwiperRef | null>(null);

  const handleNextSlide = () => {
    if (swiperRef.current && swiperRef.current.swiper) {
      const swiper = swiperRef.current.swiper;
      if (swiper.activeIndex === slides.length - 1) {
        onNextStep(StepKey.done as keyof typeof StepKey);
      } else {
        swiper.slideNext();
      }
    }
  };

  return (
    <PageTransition>
      <Swiper
        ref={swiperRef}
        modules={[Pagination, Navigation]}
        slidesPerView={1}
        pagination={{ clickable: true, el: '.custom-pagination' }}
        navigation
        spaceBetween={30}
        centeredSlides={true}
        loop={false}
        className="relative"
        allowTouchMove={false}
      >
        {slides.map((slide) => (
          <SwiperSlide key={slide.id}>
            <Box className="h-[100vh] w-full flex flex-col items-center justify-between">
              <Box className="w-full px-6 mt-6 flex flex-col items-center justify-center">
                <Image
                  src={slide.image}
                  alt={slide.title}
                  className="w-full object-cover z-10"
                />
              </Box>
              <Box className="flex flex-col gap-8 px-8 h-[38%]">
                <Box className="flex flex-col gap-4 text-center">
                  <Typography variant="h2" className="text-[38px] font-normal ">
                    {slide.title}
                  </Typography>
                  <Typography
                    variant="body2"
                    className="text-[17px] text-grayLight dark:text-grayDark"
                  >
                    {slide.description}
                  </Typography>
                </Box>
                <Box className="flex justify-center">
                  <Button
                    htmlType="button"
                    type="primary"
                    size="medium"
                    wrapperSx={{
                      margin: '0px auto',
                      marginTop: '0.5rem'
                    }}
                    className="shadow-primaryBtn"
                    onClick={handleNextSlide}
                  >
                    <Typography variant="body2">
                      {slide.step === 'done' ? 'Get Started' : 'Next'}
                    </Typography>
                  </Button>
                </Box>
              </Box>
            </Box>
          </SwiperSlide>
        ))}
        <Box className="absolute top-[58%] left-0 right-0 custom-pagination flex justify-center"></Box>
      </Swiper>
    </PageTransition>
  );
};

export default SliderOnboarding;
