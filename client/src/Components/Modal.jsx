import { useState } from "react";
import { styled } from 'styled-components'
import { CategoryCircle } from '../Components/Wishlists'
import { LimitInput } from '../Pages/Wishlist'
import Palette from "../Palette/Palette";
import { useSelector } from 'react-redux';

const ModalBackground = styled.div`
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 10;
  display: flex;
  align-items: center;
  justify-content: center;
`
const ModalContainer = styled.div`
  width: 25%;
  height: 60%;
  border-radius: 20px;
  background-color: #22221F;
  padding: 3% 2% 5% 2%;
  z-index: 20;
`
const ModalDiv = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
`
const ModalMenuDiv = styled.div`
  width: 100%;
  display: flex;
  flex-direction: row;
  align-items: center;
  margin-bottom: 15%;
  padding: 1%;
  border-bottom: 1px solid white;
`
const ModalTitleDiv = styled.div`
  width: 60%;
  text-align: left;
`
const ModalTitle = styled.span`
  width: 50%;
  color: white;
  font-weight: bold;
  font-size: 1.2rem;
`
const ModalContentDiv = styled.div`
  width: 50%;
  text-align: left;
`
const CategorySelect = styled.select`
  width: 100%;
  background-color: rgba(0,0,0,0);
  border: 0px;
  color: white;
  background-color: #22221F;
  padding: 5px;
  font-size: 1rem;
  outline: none;
`
const ModalButtonDiv = styled.div`
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: center;
  margin-top: 30%;
`
const ModalButton = styled.button`
  border-radius: 20px;
  margin: 0px 1%;
  padding: 10px;
  width: 25%;
  background-color: ${props => props.save ? '#F9591D' : '#C0C0C0'};
  color: ${props => props.save ? 'white' : 'black'};
  margin: 0px 10%;
`

export default function Modal({setOpenModal, wishlist, setWishlist, limitPrice, editMode, setEditMode, item}){
  const close = () => {
    setEditMode(false)
    setOpenModal(false)
  }

  const category = ['식비_간식','주거_통신','교통_차량','생활_마트','의류_미용','의료_건강','교육_문화','보험_세금','기타지출']
  const [addCategory, setAddCategory] = useState()
  const [addName, setAddName] = useState()
  const [addPrice, setAddPrice] = useState()

  const date = new Date()
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  const today = `${year}.${month}.${day}`;

  const targetExpend = useSelector((state) => state.targetExpend);
  const addWishlist = () =>{
    const newWishlist = {
      name: addName,
      img: '',
      price: Number(addPrice),
      category: addCategory,
      date: today,
      priority: wishlist.length + 1,
      available: targetExpend > limitPrice ? false : true,
    }
    setWishlist({ ...wishlist, list: [...wishlist.list, newWishlist] });
    setAddCategory()
    setAddName()
    setAddPrice()
    close()
  }
  const editWishlist = () => {
    const editedWishlist = {
      name: addName === undefined ? item.name : addName,
      img: item.img,
      price: addPrice === undefined ? Number(item.price) : Number(addPrice),
      category: addCategory === undefined ? item.category : addCategory,
      date: item.date,
      priority: item.priority,
      available: addPrice > limitPrice ? false : true,
    }
    const updatedWishlist = wishlist.list.map(el => {
      if (el.priority === editedWishlist.priority) {
        return editedWishlist; // priority가 같은 경우 대체
      }
        return el; // 그 외의 경우는 원래 항목 유지
    });
    setWishlist({ ...wishlist, list: updatedWishlist });
    setAddCategory()
    setAddName()
    setAddPrice()
    close()
  }
  return(
    <ModalBackground onClick={close}>
      <ModalContainer onClick={(e) => e.stopPropagation()}>
        <ModalDiv>
          <ModalMenuDiv>
            <ModalTitleDiv>
              <ModalTitle>카테고리</ModalTitle>
            </ModalTitleDiv>
            <CategoryCircle bgcolor={addCategory === undefined ? Palette[item.category] : Palette[addCategory]} />
            <ModalContentDiv>
              <CategorySelect
                value={editMode ? undefined : addCategory}
                defaultValue={editMode ? item.category : undefined}
                onChange={(e)=> setAddCategory(e.target.value)}
              >
                {category.map(el => {
                  return(
                    <option value={el}>
                      {el}
                    </option>
                  )
                })}
              </CategorySelect>
            </ModalContentDiv>
          </ModalMenuDiv>
          <ModalMenuDiv>
            <ModalTitleDiv>
              <ModalTitle>항목</ModalTitle>
            </ModalTitleDiv>
            <ModalContentDiv>
              <LimitInput
                type='input'
                value={editMode ? undefined : addName}
                defaultValue={editMode ? item.name : undefined}
                onChange={(e) => setAddName(e.target.value)}
                fontsize='1rem'
                placeholder='항목을 입력하세요'
                ></LimitInput>
            </ModalContentDiv>
          </ModalMenuDiv>
          <ModalMenuDiv>
            <ModalTitleDiv>
              <ModalTitle>금액</ModalTitle>
            </ModalTitleDiv>
            <ModalContentDiv>
              <LimitInput
                type='input'
                value={editMode ? undefined : addPrice}
                defaultValue={editMode ? item.price : undefined}
                onChange={(e) => setAddPrice(e.target.value)}
                fontsize='1rem'
                placeholder='금액을 입력하세요'
                ></LimitInput>
            </ModalContentDiv>
          </ModalMenuDiv>
          <ModalButtonDiv>
            <ModalButton save={true} onClick={editMode ? editWishlist : addWishlist}>저장</ModalButton>
            <ModalButton save={false} onClick={close}>취소</ModalButton>
          </ModalButtonDiv>
        </ModalDiv>
      </ModalContainer>
    </ModalBackground>
  )
}