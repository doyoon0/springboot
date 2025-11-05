import React from 'react';
import { TbStar } from "react-icons/tb";
import { TbStarFilled } from "react-icons/tb";
import { TbStarHalfFilled } from "react-icons/tb";

export function StarRating({ totalRate, style }) {
    const starts = [...Array(5)];
    const color = (style === "star-coral") ? "coral" : "black";

    const fillStars = Math.floor(totalRate); //채워진 별 갯수
    const halfStar = (totalRate % 1) !== 0; //True, False로 반환. 반쪽 별 여부 체크
    const emptyStar = 5 - fillStars - (halfStar ? 1 : 0); // 빈별 갯수

    return (
        <div className="star-rating">
            {/* 채워진 별 */}
            {Array.from({ length: fillStars }).map((_, i) => (
                <span key={`filled-${i}`} className={style.concat(" ", color)}>
                    <TbStarFilled />
                </span>
            ))}

            {/* 반 별 */}
            {halfStar && (
                <span key="half" className={style.concat(" ", color)}>
                    <TbStarHalfFilled />
                </span>
            )}

            {/* 빈 별 */}
            {Array.from({ length: emptyStar }).map((_, i) => (
                <span key={`empty-${i}`} className={style.concat(" ", color)}>
                    <TbStar />
                </span>
            ))}

            {/* 숫자 별점 */}
            {style === "star-coral" && (
                <span className={`${style} number`}>{totalRate}</span>
            )}

            {/* 숫자 별점 - 리뷰 */}
            {style === "star-black-big" && (
                <>
                <span className={`${style} number`}>{totalRate} /</span>
                <span className={`${style} tot-number`}>5</span>
                </>
            )}
        </div>
    );
}

