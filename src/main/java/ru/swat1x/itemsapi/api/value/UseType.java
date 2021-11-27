package ru.swat1x.itemsapi.api.value;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum UseType {

    RIGHT_MAIN_CLICK,
    RIGHT_OFF_CLICK,
    LEFT_CLICK,
    HB_SELECT,
    HB_UNSELECT

}
