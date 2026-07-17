
import { Button } from "@mui/material"
import { useContext, useEffect, useState } from "react";
import { useDispatch } from "react-redux";
import { AuthContext } from "react-oauth2-code-pkce"
import {BrowserRouter as Router, Navigate, Route, Routes, useLocation} from "react-router"
import { setCredentials } from "./store/authSlice";

function App() {

  const {token, tokenData, logIn, logOut, isAuthenticate} = useContext(AuthContext);
  const dispatch = useDispatch();
  const [authReady, setAuthReady] = useState(false);

  useEffect(() => {
    if(token){
      dispatch(setCredentials({token, user: tokenData}));
      setAuthReady(true);
    }
  }, [token, tokenData, dispatch])  
  return (
    <Router>
      {!token ? (
      <Button variant="contained" onClick={() => logIn()}>
       LOGIN
      </Button>
  ) : (
    <div>
    
    </div>
  )}
    </Router>
  )
}

export default App
