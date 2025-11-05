import { createContext, useState } from "react";

//1. Context 생성
export const ProductContext = createContext();

export const ProductProvider = ({ children }) => { 
    const [productList, setProductList] = useState([]);
    const [imgList, setImgList] = useState([]);
    const [product, setProduct] = useState({});
    

    //handling 하는애가 Provider
    return (
        <ProductContext.Provider value={{ productList, setProductList, product, setProduct, imgList, setImgList }}> 
            {children}
        </ProductContext.Provider>
    );
}
