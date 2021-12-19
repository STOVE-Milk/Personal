package com.steam.management.service;

import com.steam.management.dto.PatchRoleRequest;
import com.steam.management.dto.UserDto;
import com.steam.management.dto.UserPageResponse;
import com.steam.management.entity.User;
import com.steam.management.exception.custom.NotFoundUserException;
import com.steam.management.repository.UserRepository;
import com.steam.management.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ManagementService {
    private final UserRepository userRepository;

    private final FileUtil fileUtil;

    public UserPageResponse getUserPage(Integer page) {
        PageRequest pageRequest = PageRequest.of(page, 20);
        Page<User> userPage = userRepository.findAll(pageRequest);

        List<UserDto> users = userPage.getContent().stream()
                .map(UserDto::of)
                .collect(Collectors.toList());

        return UserPageResponse.builder()
                .pages(userPage.getTotalPages())
                .users(users)
                .build();
    }

    @Transactional
    public String patchUserRole(PatchRoleRequest request) {
        User user = userRepository.findUserById(request.getId())
                .orElseThrow(NotFoundUserException::new);
        if(request.getRole().equals("SPECIAL"))
            user.setAdmin();
        else
            user.setNormal();

        return "변경 성공";
    }

    @Transactional
    public String deleteUser(Integer id) {
        User user = userRepository.findUserById(id)
                .orElseThrow(NotFoundUserException::new);

        userRepository.delete(user);
        return "삭제 성공";
    }
}
