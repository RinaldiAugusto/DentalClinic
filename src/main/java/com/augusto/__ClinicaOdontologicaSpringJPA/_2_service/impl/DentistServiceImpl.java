package com.augusto.__ClinicaOdontologicaSpringJPA._2_service.impl;

import com.augusto.__ClinicaOdontologicaSpringJPA._2_service.IDentistService;
import com.augusto.__ClinicaOdontologicaSpringJPA._3_repository.DentistRepository;
import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.Dentist;
import com.augusto.__ClinicaOdontologicaSpringJPA.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.DentistDTOs.DentistCreateDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.DentistDTOs.DentistResponseDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.exception.ResourceNotFoundException;
import com.augusto.__ClinicaOdontologicaSpringJPA.mapper.DentistMapper;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.stream.Collectors;

import java.util.List;
import java.util.Optional;

@Service
public class DentistServiceImpl implements IDentistService {

    private DentistRepository repository;

    @Autowired
    private DentistMapper dentistMapper;

    @Autowired
    public DentistServiceImpl(DentistRepository repository) {
        this.repository = repository;
    }

    public Dentist save(Dentist dentist){
        return repository.save(dentist);
    }

    public Optional<Dentist> findById(Long id){
        return repository.findById(id);
    }

    public void update(Dentist dentist){
        repository.save(dentist);
    }

    public void delete(Long id) throws ResourceNotFoundException {
        //vamos a buscar por id el dentista
        Optional<Dentist> dentistOptional = findById(id);

        if (dentistOptional.isPresent()){
            repository.deleteById(id);
        }else {
            throw new ResourceNotFoundException("No se puedo eliminar el odontolgo con id:" + id);
        }
    }

    public List<Dentist> findAll(){
        return repository.findAll();
    }

    @Override
    public DentistResponseDTO createDentist(DentistCreateDTO dentistCreateDTO) {
        Dentist dentist = dentistMapper.toEntity(dentistCreateDTO);
        Dentist savedDentist = repository.save(dentist);
        return dentistMapper.toResponseDTO(savedDentist);
    }

    @Override
    public DentistResponseDTO updateDentist(Long id, DentistCreateDTO dentistCreateDTO) throws ResourceNotFoundException {
        Dentist existingDentist = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dentista no encontrado con ID: " + id));

        dentistMapper.updateEntityFromDTO(dentistCreateDTO, existingDentist);
        Dentist updatedDentist = repository.save(existingDentist);
        return dentistMapper.toResponseDTO(updatedDentist);
    }

    @Override
    public DentistResponseDTO findDentistDTOById(Long id) throws ResourceNotFoundException {
        Dentist dentist = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dentista no encontrado con ID: " + id));
        return dentistMapper.toResponseDTO(dentist);
    }

    @Override
    public List<DentistResponseDTO> findAllDentistDTOs() {
        return repository.findAll()
                .stream()
                .map(dentistMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

}
