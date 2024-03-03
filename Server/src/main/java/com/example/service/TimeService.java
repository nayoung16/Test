package com.example.service;

import com.example.domain.time.TimeRepository;
import com.example.domain.user.UserRepository;
import com.example.dto.TimeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class TimeService {
    private final TimeRepository timeRepository;
    private final UserRepository userRepository;

    public void save(TimeDto timeDto){
        timeRepository.save(timeDto.toEntity());
    }

    public void deleteByEmailAndTime(String email, String time){
        timeRepository.deleteByUserEmailAndTime(email, time);
    }

}
