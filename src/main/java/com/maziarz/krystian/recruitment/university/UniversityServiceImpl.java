package com.maziarz.krystian.recruitment.university;

import com.maziarz.krystian.recruitment.university.dto.UniversityRequestDto;
import com.maziarz.krystian.recruitment.university.dto.UniversityResponseDto;
import com.maziarz.krystian.recruitment.university.exception.UniversityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UniversityServiceImpl implements UniversityService {

  private final UniversityRepository universityRepository;

  private final UniversityMapper universityMapper;

  @Override
  public List<UniversityResponseDto> getAllUniversities() {
    return universityRepository.findAll().stream()
        .map(universityMapper::mapUniversityEntityToResponseDto)
        .toList();
  }

  @Override
  public UniversityResponseDto findById(Long id) {
    UniversityEntity universityEntity =
        universityRepository.findById(id).orElseThrow(() -> new UniversityNotFoundException(id));
    return universityMapper.mapUniversityEntityToResponseDto(universityEntity);
  }

  @Override
  public void deleteById(Long id) {
    universityRepository.deleteById(id);
  }

  @Override
  @Transactional
  public UniversityResponseDto addUniversity(UniversityRequestDto universityRequestDto) {
    UniversityEntity universityEntity =
        universityMapper.mapUniversityRequestDtoToEntity(universityRequestDto);
    universityRepository.save(universityEntity);
    return universityMapper.mapUniversityEntityToResponseDto(universityEntity);
  }

  @Override
  @Transactional
  public UniversityResponseDto updateUniversity(
      Long id, UniversityRequestDto universityRequestDto) {
    UniversityEntity universityEntity =
        universityRepository.findById(id).orElseThrow(() -> new UniversityNotFoundException(id));
    universityEntity.setName(universityRequestDto.getName());
    universityEntity.setAddress(universityRequestDto.getAddress());

    return universityMapper.mapUniversityEntityToResponseDto(universityEntity);
  }

  @Override
  public void checkIfUniversityExists(Long id) {
    if (!universityRepository.existsById(id)) {
      throw new UniversityNotFoundException(id);
    }
  }
}
