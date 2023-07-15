import { useNavigate } from "react-router-dom";
import { useLoginUser } from "./providers/useLoginUserProvider";
import { useCallback } from "react";

export const useLogout = () => {
  const navigate = useNavigate();
  const { setLoginUser } = useLoginUser();
  const logout = useCallback(() => {
    setLoginUser({ userId: 0, loginFlag: false });
    navigate("/");
  }, []);
  return { logout };
}
