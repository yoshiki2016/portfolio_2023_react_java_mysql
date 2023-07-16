import { ReactNode, FC } from "react";
import { Button } from "@chakra-ui/react";

type Props = {
  children: ReactNode;
  isDisabled?: boolean;
  isLoading?: boolean;
  onClick: () => void;
};

export const PrimaryButton: FC<Props> = props => {
  const {
    children,
    isDisabled = false,
    isLoading = false,
    onClick
  } = props;

  return (
    <Button
      colorScheme="twitter"
      color="white"
      isDisabled={isDisabled || isLoading}
      isLoading={isLoading}
      _hover={{ opacity: 0.8 }}
      onClick={onClick}
    >
      {children}
    </Button>
  );
};
