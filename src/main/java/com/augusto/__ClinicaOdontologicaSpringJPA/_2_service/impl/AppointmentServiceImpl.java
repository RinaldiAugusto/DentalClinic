package com.augusto.__ClinicaOdontologicaSpringJPA._2_service.impl;

import com.augusto.__ClinicaOdontologicaSpringJPA._2_service.IAppointmentService;
import com.augusto.__ClinicaOdontologicaSpringJPA._3_repository.AppointmentRepository;
import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.Appointment;
import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.Dentist;
import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.Patient;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AppointmentDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements IAppointmentService {
    private AppointmentRepository repository;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository repository) {
        this.repository = repository;
    }

    public AppointmentDTO save(AppointmentDTO appointmentDTO){
        //PERSISTIR ENTIDADES Y DEVOLVER DTOs
        //mapear entidades como DTOs a mano para conocer como se hace y luego poder usar librerias
        //instanciar una entidad de turno
        Appointment appointment = new Appointment();

        //instanciar un paciente
        Patient patient = new Patient();
        patient.setId(appointmentDTO.getPatient_id());

        //instanciar un dentista
        Dentist dentist = new Dentist();
        dentist.setId(appointmentDTO.getDentist_id());

        //seteamos paciente y dentista al turno
        appointment.setPatient(patient);
        appointment.setDentist(dentist);

        //convertir el string del turnoDTO a que es la fecha localDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(appointmentDTO.getDate(), formatter);

        //setear la fecha
        appointment.setDate(date);

        //persistir en la BD
        repository.save(appointment);

        //Ahora vamos a trabajar con el DTO que devemos devolver
        //generar una instancia de turnoDTO

        AppointmentDTO appointmentDTOreturn = new AppointmentDTO();

        appointmentDTOreturn.setId(appointment.getId());
        appointmentDTOreturn.setDate(appointment.getDate().toString());
        appointmentDTOreturn.setDentist_id(appointment.getDentist().getId());
        appointmentDTOreturn.setPatient_id(appointment.getPatient().getId());
        return appointmentDTOreturn;
    }

    public Optional<AppointmentDTO> findById(Long id){

        Optional<Appointment> appointment = repository.findById(id);
        Optional<AppointmentDTO> appointmentDTO = null;

        if (appointment.isPresent()){
            //recuperar la entidad que se encontro y guardarla en una variable appointment
            Appointment appointment1 = appointment.get();

            //trabajar sobre la informacion que tenemos que devolver: DTO
            //vamos a  crear una instancia de turno dto para devolver
            AppointmentDTO appointmentToReturn = new AppointmentDTO();
            appointmentToReturn.setId(appointment1.getId());
            appointmentToReturn.setPatient_id(appointment1.getPatient().getId());
            appointmentToReturn.setDentist_id(appointment1.getDentist().getId());
            appointmentToReturn.setDate(appointment1.getDate().toString());

            appointmentDTO = Optional.of(appointmentToReturn);
        }
        return appointmentDTO;
    }

    public AppointmentDTO update(AppointmentDTO appointmentDTO) throws Exception{

        //chequeo que el turno a actualizar exista
        if(repository.findById(appointmentDTO.getId()).isPresent()) {

            //buscar la entidad en la BD
            Optional<Appointment> appointmentEntity = repository.findById(appointmentDTO.getId());

            //instanciar un paciente
            Patient patientEntity = new Patient();
            patientEntity.setId(appointmentDTO.getPatient_id());

            //instanciar un odontólogo
            Dentist dentistEntity = new Dentist();
            dentistEntity.setId(appointmentDTO.getDentist_id());

            //seteamos el paciente y el odontólogo a nuestra entidad turno
            appointmentEntity.get().setDentist(dentistEntity);
            appointmentEntity.get().setPatient(patientEntity);

            //convertir el string del turnoDto que es la fecha a LocalDate
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(appointmentDTO.getDate(), formatter);

            //setear la fecha
            appointmentEntity.get().setDate(date);

            //persistir en la BD
            repository.save(appointmentEntity.get());

            //vamos a trabajar sobre la respuesta (dto) a devolver
            AppointmentDTO appointmentDTOToReturn = new AppointmentDTO();
            appointmentDTOToReturn.setId(appointmentEntity.get().getId());
            appointmentDTOToReturn.setPatient_id(appointmentEntity.get().getPatient().getId());
            appointmentDTOToReturn.setDentist_id(appointmentEntity.get().getDentist().getId());
            appointmentDTOToReturn.setDate(appointmentEntity.get().getDate().toString());

            return appointmentDTOToReturn;
        } else {
            throw new Exception("No se pudo actualizar el turno");
        }


    }

    public Optional<AppointmentDTO> delete(Long id) throws ResourceNotFoundException {
        //buscar la entidad por id en la BD y guardarla en un optional
        Optional<Appointment> appointmentOptional = repository.findById(id);
        Optional<AppointmentDTO> appointmentDTOOptional;

        if (appointmentOptional.isPresent()){
            //recuperamos el turno que se encontro y lo guardamos en una variable de turno
            Appointment appointment = appointmentOptional.get();

            //Vamos a devolver un dto, vamos a trabajar sobre ese dto
            //Se crea la instancia del dto
            AppointmentDTO appointmentDTO = new AppointmentDTO();
            appointmentDTO.setId(appointment.getId());
            appointmentDTO.setDentist_id(appointment.getDentist().getId());
            appointmentDTO.setPatient_id(appointment.getPatient().getId());
            appointmentDTO.setDate(appointment.getDate().toString());

            appointmentDTOOptional = Optional.of(appointmentDTO);
            return appointmentDTOOptional;

        }else {
            //Vamos a lanzar la exception
            throw new ResourceNotFoundException("No se encontro el turno con el id: " + id);
        }
    }

    public List<AppointmentDTO> findAll(){
        //Vamos a traernos las entidades de la BD y guardarlas en una lista
        List<Appointment> appointments = repository.findAll();

        //Vamos a crear una lista vacia de turnos DTOs
        List<AppointmentDTO> appointmentDTOS = new ArrayList<>();

        for(Appointment appointment: appointments){
            appointmentDTOS.add(new AppointmentDTO(appointment.getId(), appointment.getPatient().getId(),
                     appointment.getDentist().getId(), appointment.getDate().toString()));
        }
        return appointmentDTOS;
    }

}
