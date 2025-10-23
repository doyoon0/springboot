import { login, logout} from '../../feature/auth/authSlice.js';
import { validateFormCheck, validateSignupFormCheck } from '../../utils/validate.js';
import { axiosPost } from '../../utils/dataFetch.js';
import { getCartCount } from '../../feature/cart/cartAPI.js';
import { updateCartCount } from '../../feature/cart/cartSlice.js';

/**
    Id 중복 체크
*/
export const getIdCheck = (id) => async(dispatch) => {
    const url = "/member/idcheck";
    const data = { "id": id }; //axiosPost에서 json으로 보내기로 약속
    const result = await axiosPost(url, data);
    return result;
}

/**
 Signup
*/
export const getSignup = (formData, param) => async(dispatch) => {
    let result = null;
    if(validateSignupFormCheck(param)) {
        const url = "/member/signup";
        result = await axiosPost(url, formData);
    }
    return result;
}

/**
 Login
*/
export const getLogin = (formData, param) => async(dispatch) => {

    if(validateFormCheck(param)) {
        /**
            SpringBoot - @RestController, @PostMapping("/member/login")
            axios api
        */
        const url = "/member/login";
        const result = await axiosPost(url, formData);

        if(result) {
            dispatch(login({"userId": formData.id}));
            //장바구니 카운트 함수 호출
            const count = await getCartCount(formData.id);
            //cartSlice > updateCartCount : dispatch 호출
            dispatch(updateCartCount({"count": count, "type": true}));
            return true;
        }
    }
    return false;
}

export const getLogout = () => async(dispatch) => {
    dispatch(logout());
    dispatch(updateCartCount({"count": 0, "type": false}));
    return true;
}