package ru.skypro.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@Data
@AllArgsConstructor
public class ResponseWrapperAdsDto {
    private int count;
    private List<AdsDto> results;
}
