package com.ronit.job;

import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import com.ronit.repositories.CouponRepository;

//@Component
public class CouponExpirationDailyIob2 {

//		private Thread thread;
	@Autowired
	private CouponRepository couponRepository;

	@Scheduled(cron = "*/5 * * * * ?")
	public void deleteExpiredJob() {
		System.out.println(">>> " + Thread.currentThread().getName() + "  started");
		while (true) {
			try {
//					TimeUnit.HOURS.sleep(24); // for production
				TimeUnit.SECONDS.sleep(5); // for testing
			} catch (InterruptedException e) {
				break;
			}
			System.out.println(">>> " + Thread.currentThread().getName() + "  is now deleting expired coupons");
			couponRepository.deleteExpiredCoupons();
		}
		System.out.println(">>> " + Thread.currentThread().getName() + "  stop");
	}
}
