import { useEffect } from 'react';
import { RiDeleteBin6Line } from 'react-icons/ri';
import '../styles/cart.css';
import { Link, useNavigate } from 'react-router-dom';
import { CartContext } from '../context/CartContext.js';
import { useCart } from '../hooks/useCart.js';

/* Redux */
import { useSelector, useDispatch } from 'react-redux';
import { showCart, updateCart, removeCart } from '../feature/cart/cartAPI.js';

export function Cart() {
    const navigate = useNavigate(); //App.js에 정의된 전체 router들을 알고있다는 뜻
    const dispatch = useDispatch();
    const cartList = useSelector((state) => state.cart.cartList);
    const totalPrice = useSelector((state) => state.cart.totalPrice);


    useEffect(() => { dispatch(showCart()); }, []);

    return (
        <div className='cart-container'>
            <h2 className='cart-header'>장바구니</h2>
            {cartList && cartList.map(item =>
                <div key={item.pid}>
                    <div className='cart-item'>
                        {item.cid}
                        <img src={item.image} alt="" />
                        <div className='cart-item-details'>
                            <p className='cart-item-title'>{item.name}</p>
                            <p className='cart-item-title'>{item.size}</p>
                            <p className='cart-item-price'>{parseInt(item.price).toLocaleString()}원</p>
                        </div>
                        <div className='cart-quantity'>
                            <button type='button'
                                onClick={() => { item.qty > 1 && dispatch(updateCart(item.cid, '-')) }}>-</button>
                            <input type='text' value={item.qty} readOnly />
                            <button type='button'
                                onClick={() => { dispatch(updateCart(item.cid, '+')) }}>+</button>
                        </div>
                        <button className='cart-remove'
                            onClick={() => { dispatch(removeCart(item.cid)) }}>
                            <RiDeleteBin6Line />
                        </button>
                    </div>
                </div>
            )}

            {/* 주문 버튼 출력 */}
            {
                cartList && cartList.length > 0 ?
                    <>
                        <div className='cart-summary'>
                            <h3>주문 예상 금액</h3>
                            <div className='cart-summary-sub'>
                                <p className='cart-total'>
                                    <label>총 상품 가격 : </label>
                                    <span>{totalPrice.toLocaleString()}원</span>
                                </p>
                                <p className='cart-total'>
                                    <label>총 할인 가격 : </label>
                                    <span>-0원</span>
                                </p>
                                <p className='cart-total'>
                                    <label>총 배송비 : </label>
                                    <span>0원</span>
                                </p>
                            </div>
                            <p className='cart-total2'>
                                <label>총 금액: </label>
                                <span>{totalPrice.toLocaleString()}원</span>
                            </p>
                        </div>
                        <div className='cart-actions'>
                            <button type='button' onClick={() => {
                                //props로 데이터만 보내는게아니라 이 페이지로 넘어가면서 데이터를 함께 보내는 방식
                                //navigate("주소", "전송객체");
                                //"주소"로 이동하는건 useNavigate, "전송객체"는 useLocation이 관리 : 객체임을 state(선언)하고 cartList를 보냄. 객체니까 {}
                                navigate("/checkout", {state: {cartList : cartList, totalPrice : totalPrice}}); 
                            }}>주문하기</button>

                        </div>
                    </>
                    : <div>
                        <p>장바구니에 담은 상품이 없습니다. &nbsp;&nbsp;&nbsp;&nbsp;
                            <Link to="/all">상품보러가기</Link>
                        </p>
                        <img src="/images/cart.jpg" style={{ width: "50%", marginTop: "20px" }} />
                    </div>
            }
        </div>
    );
}
