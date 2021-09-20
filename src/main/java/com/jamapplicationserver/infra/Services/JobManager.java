/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Services;

import org.quartz.*;
import java.time.*;

/**
 *
 * @author dada
 */
public class JobManager {
    
    private final Scheduler scheduler;
    
    private JobManager(Scheduler scheduler) {
        this.scheduler = scheduler;
    }
    
    public void startScheduler() {
        
        try {
            
            this.scheduler.start();
            
        } catch(SchedulerException e) {
            e.printStackTrace();
        }
        
    }
    
    public JobManager addJob(Class jobClass, Trigger trigger) {
        
        try {
            
            final JobDetail job =
                    JobBuilder
                            .newJob(jobClass)
                            .build();
            
            this.scheduler.scheduleJob(job, trigger);
        } catch(SchedulerException e) {
            e.printStackTrace();
        }
        
        return this;
    }
    
    public JobManager addJob(Class jobClass, Trigger trigger, JobKey key) {
        
        try {
            
            final JobDetail job =
                    JobBuilder
                        .newJob(jobClass)
                        .withIdentity(key)
                        .build();
            
            this.scheduler.scheduleJob(job, trigger);
        } catch(SchedulerException e) {
            e.printStackTrace();
        }
        
        return this;
    }
    
    public Trigger getEverySecondsTrigger(int sec) {
        return TriggerBuilder
                .newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule("*/" + sec + " * * ? * *"))
                .build();
    }
    
    public Trigger getEveryMinuteTrigger(int min) {
        return TriggerBuilder
                .newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule("0 */" + min + " * ? * *"))
                .build();
    }
    
    public Trigger getEveryHourTrigger(int hour) {
        return TriggerBuilder
                .newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 0/" + hour + " ? * *"))
                .build();
    }
    
    public Trigger getDailyTrigger(int hour, int min) {
        return TriggerBuilder
                .newTrigger()
                .withSchedule(
                        CronScheduleBuilder.cronSchedule(
                                "0 " + min + " " + hour + " * * ? *"
                        )
                )
                .build();
    }
    
    public Trigger getEveryWeekDayTrigger(int hour, int min) {
        return TriggerBuilder
                .newTrigger()
                .withSchedule(
                        CronScheduleBuilder.cronSchedule(
                                "0 " + min + " " + hour + " ? SUN-WED,SAT *"
                        )
                )
                .build();
    }
    
    public Trigger getWeeklyTrigger(DayOfWeek day, int hour, int min) {
        
        String dayOfWeek;
        switch(day) {
            case SATURDAY:
                dayOfWeek = "SAT";
                break;
            case SUNDAY:
                dayOfWeek = "SUN";
                break;
            case MONDAY:
                dayOfWeek = "MON";
                break;
            case TUESDAY:
                dayOfWeek = "TUE";
                break;
            case WEDNESDAY:
                dayOfWeek = "WED";
                break;
            case THURSDAY:
                dayOfWeek = "THU";
                break;
            case FRIDAY:
                dayOfWeek = "FRI";
                break;
            default:
                dayOfWeek = "SAT";
        }
        
        return TriggerBuilder
                .newTrigger()
                .withSchedule(
                        CronScheduleBuilder.cronSchedule(
                                "0 " + min + " " + hour + " ? * " + dayOfWeek + " *"
                        )
                )
                .build();
        
    }
    
    public Trigger getMonthlyTrigger(int day, int hour, int min) {
        return TriggerBuilder
                .newTrigger()
                .withSchedule(
                        CronScheduleBuilder.cronSchedule(
                                "0 " + min + " " + hour + " " + day + " * ? *"
                        )
                )
                .build();
    }
    
    public Trigger getYearlyTrigger(Month month, DayOfWeek day, int hour, int min) {
        return TriggerBuilder
                .newTrigger()
                .withSchedule(
                        CronScheduleBuilder.cronSchedule(
                                ""
                        )
                )
                .build();
    }
    
    public static JobManager getInstance() {
        
        if(JobManagerHolder.INSTANCE == null) {
            
            try {
            
                SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
                Scheduler scheduler = schedFact.getScheduler();
                
                JobManagerHolder.INSTANCE = new JobManager(scheduler);

            } catch(SchedulerException e) {
                e.printStackTrace();
            }
            
        }
        
        return JobManagerHolder.INSTANCE;
    }
    
    private static class JobManagerHolder {

        private static JobManager INSTANCE;
    }
}
