import { useSnackbar } from 'notistack';

let useSnackbarRef: any;

export default useSnackbarRef;

export const SnackbarUtilsConfiguration: React.FC = () => {
  useSnackbarRef = useSnackbar();
  return null;
};
