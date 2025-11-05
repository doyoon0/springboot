import React, {useContext, useRef } from 'react';
import { AuthContext } from '../context/AuthContext.js';
import { Navigate } from 'react-router-dom';
import { useSelector } from 'react-redux';

export function ProtectedPageRoute({children}) {
    const isAlert = useRef(false);
    // const { isLogin } = useContext(AuthContext);
    const isLogin = useSelector((state) => state.auth.isLogin);

    if(!isLogin) { //isLogin = false
        if(!isAlert.current) {
            alert("로그인이 필요한 서비스입니다.\n로그인 페이지로 이동합니다.");
            isAlert.current = true;
        }
        return <Navigate to="/login"/> //호출이 되는 단계에서 Link처럼 클릭 이벤트 없어도 실시간으로 페이지 이동됨.
    } else {
        isAlert.current = true;
        return children;
    }
}

