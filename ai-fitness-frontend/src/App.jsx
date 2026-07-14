
import { Button } from "@mui/material"
import { useState } from "react";
import { AuthContext } from "react-oauth2-code-pkce"
import {BrowserRouter as Router, Navigate, Route, Routes, useLocation} from "react-router"
import { setCredentials } from "./store/authSlice";

function App() {

  const {token, tokenData, logIn, logOut, isAuthenticate} = useContext(AuthContext);
  const dispatch = useDispatch();
  const [authReady, setAuthReady] = useStatete(false);

  useEffect(() => {
    if(token){
      dispatch(setCredentials({token, user: tokenData}));
      setAuthReady(true);
    }
  }, [token, tokenData, dispatch])  
  return (
    <Router>
      <Button variant="contained">
       LOGIN
      </Button>
    </Router>
  )
}

export default App
