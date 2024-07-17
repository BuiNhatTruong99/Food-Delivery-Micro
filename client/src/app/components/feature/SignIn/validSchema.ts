import { z } from 'zod';

const SignInSchema = z.object({
  email: z.string().email(),
  password: z.string().min(6).max(20)
});

type TSignInSchema = z.infer<typeof SignInSchema>;

export { SignInSchema };
export type { TSignInSchema };
