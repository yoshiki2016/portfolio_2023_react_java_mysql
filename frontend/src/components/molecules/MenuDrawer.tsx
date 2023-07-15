import { Button, Drawer, DrawerBody, DrawerContent, DrawerOverlay } from "@chakra-ui/react";
import { FC, memo } from "react";
import { useNavigate } from "react-router-dom";
import { useLogout } from "../../hooks/useLogout";

type Props = {
  isOpen: boolean;
  onClose: () => void;
};

export const MenuDrawer: FC<Props> = memo(props => {
  const { isOpen, onClose } = props;
  const navigate = useNavigate();
  const { logout } = useLogout();
  const onClickUserSettings = () => navigate("/user_setting");
  const onClickLogout = () => logout();
  return (
    <Drawer placement="left" size="xs" onClose={onClose} isOpen={isOpen}>
      <DrawerOverlay>
        <DrawerContent>
          <DrawerBody as="nav" bg="gray.100">
            <Button w="100%" onClick={onClickUserSettings}>ユーザー設定</Button>
            <Button w="100%" onClick={onClickLogout}>ログアウト</Button>
          </DrawerBody>
        </DrawerContent>
      </DrawerOverlay>
    </Drawer>
  );
});
