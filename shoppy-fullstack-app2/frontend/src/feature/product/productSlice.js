import { createSlice } from '@reduxjs/toolkit'

const initialState = {
    productList: [], //출력용 - 2차원 배열
    products: [], //원본 - 1차원 배열
    product: {},
}

export const productSlice = createSlice({
    name: 'product',
    initialState,
    reducers: {

        createProduct(state, action) {
            const {productList, products} = action.payload;
            state.products = products;
            state.productList = productList;
        },

        filterProduct(state, action) {
            //const pid = action.payload;
            const { pid } = action.payload;
            
            //1. productList가 2차원 배열이므로 flat() 함수를 이용하여 1차원 변경 후 filter
            // const [filterProduct] = productList.flat().filter((item) => item.pid === pid);
            // state.product = filterProduct;

            //2. product 1차원 배열에서 find 함수
            //imgList는 product 안에 다 있으니까 굳이 따로 선언X
            state.product = state.products.find((item) => item.pid === pid);
        }
    },
})

// Action creators are generated for each case reducer function
export const { createProduct, filterProduct } = productSlice.actions

export default productSlice.reducer