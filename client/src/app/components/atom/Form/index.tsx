import { DetailedHTMLProps, FC, FormHTMLAttributes } from 'react';

interface IForm
  extends DetailedHTMLProps<FormHTMLAttributes<HTMLFormElement>, any> {}
const Form: FC<IForm> = ({ children, ...props }) => {
  return <form {...props}>{children}</form>;
};

export default Form;
