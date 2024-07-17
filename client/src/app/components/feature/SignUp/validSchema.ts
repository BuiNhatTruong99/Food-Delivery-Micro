import { z } from 'zod';

const SignUpSchema = z.object({
  full_name: z.string().min(3).max(20),
  email: z.string().email(),
  password: z.string().min(6).max(20)
});

type TSignUpSchema = z.infer<typeof SignUpSchema>;

export { SignUpSchema };
export type { TSignUpSchema };
