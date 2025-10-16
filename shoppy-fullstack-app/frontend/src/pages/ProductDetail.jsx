import React, { useState, useEffect, useContext } from 'react';
import { useParams } from 'react-router-dom';
import { PiGiftThin } from "react-icons/pi";
import { ImageList } from '../components/commons/ImageList.jsx';
import { StarRating } from '../components/commons/StarRating.jsx';
import { Detail, DetailImages, DetailInfo } from '../components/detailTabs/Detail.jsx';
import { Review } from '../components/detailTabs/Review.jsx';
import { QnA } from '../components/detailTabs/QnA.jsx';
import { Return } from '../components/detailTabs/Return.jsx';

/* Redux */
import { useDispatch, useSelector } from 'react-redux';
import { addCart } from '../feature/cart/cartAPI.js';
import { getProduct } from '../feature/product/productAPI.js'

// export function ProductDetail({ addCart }) {
export function ProductDetail() {
    const dispatch = useDispatch();
    const { pid } = useParams(); //객체로 이 보따리에 담아주면 구조분해할당으로 풀어본다
    const product = useSelector((state) => state.product.product);
    const imgList = useSelector((state) => state.product.product.imgList);


    // const { addCart } = useCart(); //useCart.js에 선언된 변수랑 이름 맞춰야함
    // const { filterProduct } = useProduct();
    // const { product, imgList} = useContext(ProductContext);

    
    const [size, setSize] = useState('XS');
    const tabLabels = ['DETAIL', 'REVIEW', 'Q&A', 'RETURN & DELIVERY'];
    const [tabName, setTabName] = useState('detail');
    const tabEventNames = ['detail', 'review', 'q&a', 'return'];

    useEffect(() => {
        // filterProduct(pid);
        dispatch(getProduct(pid));
    }, []);

    // //쇼핑백 추가하기 ==> 직접 넣어버리기.
    // const handleAddCartItem = () => {
    //     // alert("상품이 카트에 추가되었습니다.");
    //     const cartItem = {
    //         pid: product.pid,
    //         size: size,
    //         qty: 1
    //     }
    //     // addCart(cartItem);
    //    dispatch(addCart(cartItem)); //addaCart 호출 시 dispatch 전송!!
        
    // }

    return (
        <div className='content'>
            <div className='product-detail-top'>
                <div className='product-detail-image-top'>
                    <img src={product.image} />
                    <ImageList className='product-detail-image-top-list' imgList={imgList} />
                </div>
                <ul className='product-detail-info-top'>
                    <li className='product-detail-title'>{product.name}</li>
                    <li className='product-detail-title'>{`${parseInt(product.price).toLocaleString()} 원`}</li>
                    <li className='product-detail-subtitle'>{product.info}</li>
                    <li className='product-detail-subtitle-star'>
                        <StarRating totalRate={product.rate} style="star-coral" />
                        <span>527개 리뷰 &nbsp;&nbsp; {">"} </span>
                    </li>
                    <li className='product-detail-subtitle-star'>
                        <p className='product-detail-box'>신규회원, 무이자할부 등</p>
                    </li>
                    <li className='flex'>
                        <button className='product-detail-button size'>사이즈</button>
                        <select
                            className='product-detail-select2'
                            onChange={(e) => setSize(e.target.value)}>
                            <option value="XS">XS</option>
                            <option value="S">S</option>
                            <option value="M">M</option>
                            <option value="L">L</option>
                            <option value="XL">XL</option>
                        </select>
                    </li>
                    <li className='flex'>
                        <button type='button' className='product-detail-button order'>바로 구매</button>
                        <button type='button' className='product-detail-button cart' onClick={() => {dispatch(addCart(product.pid, size))}}>쇼핑백 담기</button>
                        <div type='button' className='gift'>
                            <PiGiftThin />
                            <div className='gift-span'>선물하기</div>
                        </div>
                    </li>
                    <li>
                        <ul className='product-detail-summary-info'>
                            <li>상품 요약 정보</li>
                        </ul>
                    </li>
                </ul>
            </div>

            <div className='product-detail-tab'>
                <ul className='tabs'>
                    {tabLabels && tabLabels.map((label, i) =>
                        <li className={tabName === tabEventNames[i] ? "active":"" }>
                            <button type='button' onClick={(e) => setTabName(tabEventNames[i])}>{label}</button>
                        </li>
                    )}
                </ul>
                    {tabName === "detail" && <Detail imgList={imgList} info={product.detailInfo} />}
                    {tabName === "review" && <Review />}
                    {tabName === "q&a" && <QnA />}
                    {tabName === "return" && <Return />}

            </div>
            <div style={{marginBottom:"50px"}}></div>

        </div>
    );
}

