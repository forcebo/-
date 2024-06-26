package com.finaldesign.backend.service.student.reserve;

import com.finaldesign.backend.pojo.Reserve;
import com.finaldesign.backend.pojo.Result;

public interface ReserveService {
    Result reserveTeacher(Reserve reserve);
    Result getReservesByStudentId();
    Result checkIfReserve(Integer teacherId);
    Result cancelReserve(Integer teacherId);
    Result getTop5TeacherForReserveStatistics();
    Result getReservesByTeacherId(Integer page);
    Result confirmReserve(Integer ReserveId);
}
