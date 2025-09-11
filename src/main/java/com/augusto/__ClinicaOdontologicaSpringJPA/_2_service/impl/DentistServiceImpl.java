package com.augusto.__ClinicaOdontologicaSpringJPA._2_service.impl;

import com.augusto.__ClinicaOdontologicaSpringJPA._2_service.IDentistService;
import com.augusto.__ClinicaOdontologicaSpringJPA._3_repository.DentistRepository;
import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.Dentist;
import com.augusto.__ClinicaOdontologicaSpringJPA.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DentistServiceImpl implements IDentistService {

    private DentistRepository repository;

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

}
