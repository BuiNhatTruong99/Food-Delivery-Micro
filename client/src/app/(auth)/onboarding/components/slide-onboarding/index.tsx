import { PageTransition } from '@/components/molecule';
import { IMAGES_DEFAULT } from '@/constant';
import { ButtonBase } from '@mui/material';
import Image from 'next/image';
import { Swiper } from 'swiper/react';

const SliderOnboarding = () => {
  return (
    <PageTransition>
      <Swiper
        slidesPerView={1}
        onSlideChange={() => console.log('slide change')}
        onSwiper={(swiper) => console.log(swiper)}
      >
        <div
          style={{
            backgroundImage: `url(${IMAGES_DEFAULT.onboarding.background.src})`
          }}
          className="h-[100vh] bg-contain bg-no-repeat flex flex-col items-start justify-between p-5 gap-5"
        >
          <div className="flex flex-col items-start justify-start gap-5 pt-36">
            <Image
              width={311}
              height={111}
              src={IMAGES_DEFAULT.onboarding.title}
              alt={IMAGES_DEFAULT.onboarding.title.toString()}
            />
            <p className="text-xl text-darkDefault max-w-[266px]">
              Your favorites foods delivered fast at your door.
            </p>
          </div>
          <div className="w-full text-center px-10 mb-[10%]">
            <ButtonBase
              className="w-full h-12 rounded-[100px]"
              style={{
                backgroundColor: 'rgba(255, 255, 255, 0.5)',
                border: '1px solid #FFFFFF'
              }}
              // onClick={onNext}
            >
              <span className="text-xl">Get started</span>
            </ButtonBase>
          </div>
        </div>
      </Swiper>
    </PageTransition>
  );
};

export default SliderOnboarding;
