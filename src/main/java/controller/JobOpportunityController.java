package controller;

import dao.DaoFactory;
import dao.JobOpportunityDao;
import model.Curriculum;
import model.JobOpportunity;
import model.User;

import java.util.ArrayList;

public class JobOpportunityController {
    JobOpportunityDao jobOpportunityDao = DaoFactory.getJobOpportunityDao();

    //TODO - buscar usuarios e poder visualizar seus curriculos pela sua vaga cadastada
    public void save(JobOpportunity jobOpportunity) throws Exception {
//        TODO - Fazer verificações
        if (jobOpportunity.getId() == 0) {
            jobOpportunityDao.save(jobOpportunity);
            UserController.getInstance().getSessionUser().setJobOpportunity(jobOpportunity);
            UserController.getInstance().getUserDao().save(UserController.getInstance().getSessionUser());
        } else {
            jobOpportunityDao.update();
        }
    }

    public ArrayList<JobOpportunity> listAll() {
        ArrayList<JobOpportunity> jobs = (ArrayList<JobOpportunity>) jobOpportunityDao.findAll();
        return jobs;
    }

    public JobOpportunity jobById(int id) {
        JobOpportunity jobOpportunity = jobOpportunityDao.findById(id);
        return jobOpportunity;
    }

    public ArrayList<JobOpportunity> listOpenjobs() {
        ArrayList<JobOpportunity> jobsOpen = (ArrayList<JobOpportunity>) jobOpportunityDao.listOpenJobs();
        return jobsOpen;
    }

    public boolean userInJob(JobOpportunity job, int id) {
        for (User user : job.getUsers()) {
            if (user.getId() == UserController.getInstance().getSessionUser().getId()) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<JobOpportunity> listJobsByArea(String area) throws Exception {
        if (area != null) {
            ArrayList<JobOpportunity> jobs = (ArrayList<JobOpportunity>) jobOpportunityDao.listJobsByArea(area);
            if (jobs != null) {
                return jobs;
            } else {
                throw new Exception("Nenhuma vaga foi encontrada");
            }
        } else {
            throw new Exception("Você deve preencher o campo área");
        }

    }
}
