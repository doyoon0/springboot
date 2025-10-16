//파일명에 use를 붙여야 react가 인식함
import React, { useContext } from 'react';
import { CartContext } from '../context/CartContext.js';
import { cartItemsCheck, cartItemsAddInfo, getTotalPrice } from '../utils/cart.js';
import { axiosData } from '../utils/dataFetch.js'
/**
 * CartContext를 제어하는 함수를 가진 커스텀 훅
 */
export function useCart() {

    const { setCartCount, setCartList, cartList, cartCount, totalPrice, setTotalPrice } = useContext(CartContext);

    //컨텍스트 객체의 값을 변경하는 함수 정의
    //장바구니 아이템 추가 (ProductDetail)
    const addCart = (addItem) => {
        setCartList(cartItemsCheck(cartList, addItem));
        //장바구니 카운트는 전체 수량 기준으로 증가
        setCartCount(cartCount + 1);
    }

    //장바구니 출력 : 장바구니 아이템 <-- 이미지, 상품명, 상품가격 추가
    const showCart = () => {
        const fetch = async () => {
            const jsonData = await axiosData('/data/products.json');
            setCartList(cartItemsAddInfo(jsonData, cartList)); //cart.js에서 정의. 장바구니에 담을 아이템정보
            setTotalPrice(getTotalPrice(jsonData, cartList)); //cart.js에서 정의. 아이템 정보 다 넘긴뒤 해당 아이템만 누적합
        }
        fetch();
    }

    //장바구니 수량 변경
    const updateCart = (cid, type) => {
        //카트 수량 변경
        setCartList((cartList) => cartList.map((item) =>
            item.cid === cid ? //cid 같은지 체크(1)
                type === '+' ? { ...item, qty: item.qty + 1 } : //type이 +/- 체크(2)
                    item.qty > 1 ? //qty가 1보다 큰지 체크(3)
                        { ...item, qty: item.qty - 1 } :
                        item
                : item
        ));

        //장바구니 해당아이템 개별 수량 및 금액변경 : Header
        type === "+" ?
            setCartCount(cartCount + 1) :
            cartCount > 0 ?
                setCartCount(cartCount - 1) : setCartCount(cartCount);

        const findItem = cartList.find((item) => item.cid === cid)
        type === '+' ?
            setTotalPrice(totalPrice + findItem.price) :
            findItem.qty > 1 ?
                setTotalPrice(totalPrice - findItem.price) :
                setTotalPrice(totalPrice)
    }

    //장바구니 아이템 삭제
    const removeCart = (cid, qty, price) => {
        // const findItem = cartList.find((item) => item.cid === cid); //해당하는 아이템 찾기
        //cartList 다시 불러와야함(초기값은 빈배열임)
        setCartList((cartList) => {
            return cartList.filter(item => !(item.cid === cid)); //삭제한것 제외하고 전부 
        });
        setTotalPrice(totalPrice - (qty * price)); //몇 개가 있든 한번에 삭제
        setCartCount(cartCount - qty);  //Header 수량 경경
    }


    return { addCart, showCart, updateCart, removeCart }
}

