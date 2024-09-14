import { ThemeProvider as MaterialTailwindProvider } from "@material-tailwind/react";
import PropTypes from "prop-types";

export function ThemeProvider({ children }) {
  return (
    <MaterialTailwindProvider theme={{}}>{children}</MaterialTailwindProvider>
  );
}

ThemeProvider.propTypes = {
  children: PropTypes.node.isRequired,
};
