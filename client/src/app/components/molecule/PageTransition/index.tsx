'use client';

import { motion } from 'framer-motion';
import React from 'react';

const PageTransition = ({
  children,
  className,
  animation = 'swipe-left'
}: {
  children: React.ReactNode;
  className?: string;
  animation?: 'swipe-left' | 'swipe-up';
}) => {
  const animationProps = {
    'swipe-left': {
      initial: { opacity: 0, x: 100 },
      animate: { opacity: 1, x: 0 },
      exit: { opacity: 0, x: -100 }
    },
    'swipe-up': {
      initial: { opacity: 0, y: 800 },
      animate: { opacity: 1, y: 0 },
      exit: { opacity: 0, y: -500 },
      transition: {
        duration: 0.5
      }
    }
  };
  return (
    <motion.div {...animationProps[animation]} className={className}>
      {children}
    </motion.div>
  );
};

export default PageTransition;
