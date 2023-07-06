import { ChakraProvider } from "@chakra-ui/react";
import theme from "./theme/theme";
import { Login } from "./components/pages/Login";

function App() {
  return (
    <ChakraProvider theme={theme}>
      <Login />
    </ChakraProvider>
  );
}

export default App;
