import { useContext } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { FiShoppingBag } from "react-icons/fi";
import { GiShoppingCart } from "react-icons/gi";
import { AuthContext } from '../../context/AuthContext.js';
import { useAuth } from '../../hooks/useAuth.js';

import { useSelector, useDispatch } from 'react-redux';
import { getLogout } from '../../feature/auth/authAPI.js';

export function Header() {
    // const { cartCount } = useContext(CartContext); //구조분해할당으로 받음
    // const { isLogin } = useContext(AuthContext);
    // const { handleLogout} = useAuth();

    const dispatch = useDispatch();    
    const isLogin = useSelector((state) => state.auth.isLogin);
    const role = useSelector((state) => state.auth.role);
    const cartCount = useSelector((state) => state.cart.cartCount);
    const navigate = useNavigate();
//     let userId = null;
//     if(isLogin) userId  = JSON.parse(localStorage.getItem("loginInfo")).userId;

    //isLogin이 true일 때만 localStorage 접근
    let userId = null;

    if (isLogin) {
      const loginInfo = localStorage.getItem("loginInfo");
      if (loginInfo) {
        userId = JSON.parse(loginInfo).userId;
      }
    }


    const handleLogout = () => {
        const succ = dispatch(getLogout());
//         const loginInfo = localStorage.getItem("loginInfo");
        if(succ) {
            alert("로그아웃 되었습니다.");
            navigate("/");
        }
    }

    return (
        <div className="header-outer">
            <div className="header">
                <Link to="/" className='header-left'>
                    <FiShoppingBag />
                    <span>Shoppy-redux(Toolkit) ::fullstack JPA</span>
                </Link>
                <nav className='header-right'>
                    {isLogin && <span>반갑습니다, [{userId}::{role}] 님</span>}
                    <Link to="/all">Products</Link>
                    <Link to="/cart" className="header-icons-cart-link">
                        <GiShoppingCart className='header-icons' />
                        <span className='header-icons-cart'>{cartCount}</span>
                    </Link>
                    {isLogin ?
                            <button type="button" onClick={handleLogout}>Logout</button>
                        :
                        <Link to="/login">
                            <button type="button">Login</button>
                        </Link>
                    }
                    <Link to="/signup">
                        <button type="button">Signup</button>
                    </Link>
                    {isLogin &&
                        <Link to="/support">
                            <button type="button">Support</button>
                        </Link>
                    }
                   { role === "ROLE_ADMIN" &&
                       <Link to="/admin">
                            <button type="button">Admin</button>
                       </Link>
                   }
                </nav>
            </div>
        </div>
    );
}