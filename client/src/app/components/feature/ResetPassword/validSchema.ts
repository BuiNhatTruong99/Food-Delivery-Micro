import { z } from 'zod';

export const ResetPasswordSchema = z.object({
  email: z.string().email()
});

export type TResetPasswordSchema = z.infer<typeof ResetPasswordSchema>;
