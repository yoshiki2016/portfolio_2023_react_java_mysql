import { useNavigate } from "react-router-dom";
import { Center, Stack } from "@chakra-ui/react";

import { PrimaryButton } from "../atoms/button/PrimaryButton";

export const IncorrectLogin = () => {
  const navigate = useNavigate();
  return (
    <Center h="100vh">
      <Stack spacing={6}>
        <p>不正なログインです</p>
        <PrimaryButton onClick={() => { navigate("/") }}>ログインページへ</PrimaryButton>
      </Stack>
    </Center>
  );
}
