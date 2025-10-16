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
    const cartCount = useSelector((state) => state.cart.cartCount);
    const navigate = useNavigate();

    const handleLogout = () => {
        const succ = dispatch(getLogout());
        const loginInfo = localStorage.getItem("loginInfo");
        if(succ && loginInfo === null) { 
            alert("로그아웃 되었습니다.");
            navigate("/");
        }
    }

    return (
        <div className="header-outer">
            <div className="header">
                <Link to="/" className='header-left'>
                    <FiShoppingBag />
                    <span>Shoppy-redux(Toolkit)</span>
                </Link>
                <nav className='header-right'>
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
                </nav>
            </div>
        </div>
    );
}