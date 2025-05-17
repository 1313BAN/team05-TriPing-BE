package com.ssafy.enjoytrip.domain.user.service;

import com.ssafy.enjoytrip.domain.user.dto.PasswordChangeRequest;
import com.ssafy.enjoytrip.domain.user.dto.UserIdResponse;
import com.ssafy.enjoytrip.domain.user.exception.UserException;
import com.ssafy.enjoytrip.exception.ErrorCode;
import com.ssafy.enjoytrip.util.ValidUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.enjoytrip.domain.user.model.User;
import com.ssafy.enjoytrip.domain.user.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;

	@Override
	public UserIdResponse registerUser(User user) {
		if (!ValidUtil.isValidEmail(user.getEmail()) || !ValidUtil.isValidPassword(user.getPassword())) {
			throw new UserException(ErrorCode.INVALID_FORMAT);
		}

		User existing = userMapper.selectByEmail(user.getEmail());
		if (existing != null) {
			throw new UserException(ErrorCode.DUPLICATE_EMAIL);
		}

		// 비밀번호 암호화
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		userMapper.insertUser(user);

		// 방금 등록된 user 반환 (id 포함해서)
		return new UserIdResponse(user.getId());
	}



	public UserIdResponse updateUser(Long userId, User updatedData) {
		User existing = userMapper.selectUserById(userId);
		if (existing == null) {
			throw new UserException(ErrorCode.USER_NOT_FOUND);
		}

		if (!ValidUtil.isValidPassword(updatedData.getPassword())) {
			throw new UserException(ErrorCode.INVALID_FORMAT);
		}
		existing.setName(updatedData.getName());
		existing.setEmail(updatedData.getEmail());
		existing.setNickname(updatedData.getNickname());

		// ✅ 비밀번호가 null 또는 공백이 아닐 경우에만 암호화
		if (updatedData.getPassword() != null && !updatedData.getPassword().isBlank()) {
			String encryptedPassword = passwordEncoder.encode(updatedData.getPassword());
			existing.setPassword(encryptedPassword);
		}



		userMapper.updateUser(existing);
		return new UserIdResponse(existing.getId());
	}

	@Override
	public void deleteUser(Long userId) {
		User existing = userMapper.selectUserById(userId);
		if (existing == null) {
			throw new UserException(ErrorCode.USER_NOT_FOUND);
		}

		userMapper.deleteUserById(userId);
	}

		@Override
		public User getUserById(Long userId) {
			User user = userMapper.selectUserById(userId);
			if (user == null) {
				throw new UserException(ErrorCode.USER_NOT_FOUND);
			}
			return user;
		}

	@Override
	public void changePassword(Long userId, PasswordChangeRequest request){
		User existing = userMapper.selectUserById(userId);
		//1.사용자 조회
		if(existing == null){
			throw new UserException(ErrorCode.USER_NOT_FOUND);
		}

		//2. 현재 비밀번호 일치 여부 확인
		boolean isMatched = passwordEncoder.matches(request.getCurrentPassword(), existing.getPassword());
		if(!isMatched){
			throw new UserException(ErrorCode.INVALID_PASSWORD);
		}

		//3.  새 비밀번호 유효성 검사(정규식 기반)
		if (!ValidUtil.isValidPassword(request.getNewPassword())){
			throw new UserException(ErrorCode.INVALID_FORMAT);
		}

		System.out.println(request.getNewPassword());
		// 4. 새 비밀번호 암호화
		String encodedPassword = passwordEncoder.encode(request.getNewPassword());

		// 5. DB 업데이트
		userMapper.updatePasswordById(userId, encodedPassword);
	}

}
