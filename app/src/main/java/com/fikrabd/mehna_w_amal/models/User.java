package com.fikrabd.mehna_w_amal.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class User implements Parcelable   {

    @SerializedName("status")
    private String status="";

    @SerializedName("amount")
    private String amount="";

    @SerializedName("id")
    private String id="";

    @SerializedName("currency")
    private String currency="";


    @SerializedName("user_type")
    private String userType="";


    @SerializedName("name")
    private String name="";

    @SerializedName("age")
    private String age="";

    @SerializedName("commercial_activities")
    private String commercialActivities="";

    @SerializedName("commercial_registration_no")
    private String commercialActivitiesNo="";


    @SerializedName("district")
    private String district="";


    @SerializedName("experience")
    private String experience="";

    @SerializedName("job")
    private String job="";


    @SerializedName("latitude")
    private String latitude="";

    @SerializedName("longitude")
    private String longitude="";


    @SerializedName("phone")
    private String phone="";

    @SerializedName("static_phone_number")
    private String staticPhoneNumber="";


    @SerializedName("hourly_salary")
    private String hourlySalary="";

    @SerializedName("salary_per_day")
    private String salaryPerDay="";

    @SerializedName("salary_per_month")
    private String salaryPerMonth="";

    @SerializedName("website_url")
    private String websiteUrl="";


    @SerializedName("category")
    private DefaultData category=null;

    @SerializedName("sub_category")
    private List<DefaultData> sub_category=null;


    @SerializedName("country")
    private DefaultData country=null;

    @SerializedName("state")
    private DefaultData state=null;

    @SerializedName("desire_to_work")
    private DefaultData desireToWork=null;

    @SerializedName("have_licence")
    private DefaultData haveLicence=null;


    @SerializedName("availability")
    private DefaultData availability=null;

    @SerializedName("gender")
    private DefaultData gender=null;

    @SerializedName("nationality")
    private DefaultData nationality=null;

    @SerializedName("permanence_type")
    private DefaultData permanenceType=null;


    @SerializedName("image")
    private String image;

    @SerializedName("profession_licence")
    private String professionLicence="";

    @SerializedName("resume")

    private String resume="";

    @SerializedName("rating")
    private Rating rating=null;


    @SerializedName("email")
    private String email="";

    @SerializedName("password")
    private String password="";



    @SerializedName("country_id")
    private String countryId="";

    @SerializedName("state_id")
    private String stateId="";

    @SerializedName("nationality_id")
    private String nationalityId="";

    @SerializedName("desire_work")
    private String desireWork="";


    private String categoryId="";


    private String subCategoryId="";


    private String permanenceTypeId="";


    private String haveLicenceId="";


    private String genderId="";

    @SerializedName("resume_type")
    private String resumeType="";


    private String static_phone="";

    private String website="";

    public String getStatic_phone() {
        return static_phone;
    }

    public void setStatic_phone(String static_phone) {
        this.static_phone = static_phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public User() {

    }

    protected User(Parcel in) {
        status = in.readString();
        amount = in.readString();
        id = in.readString();
        currency = in.readString();
        userType = in.readString();
        name = in.readString();
        age = in.readString();
        website=in.readString();
        static_phone=in.readString();
        commercialActivities = in.readString();
        commercialActivitiesNo = in.readString();
        district = in.readString();
        experience = in.readString();
        job = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        phone = in.readString();
        staticPhoneNumber = in.readString();
        hourlySalary = in.readString();
        salaryPerDay = in.readString();
        salaryPerMonth = in.readString();
        websiteUrl = in.readString();
        image = in.readString();
        professionLicence = in.readString();
        resume = in.readString();
        category=in.readParcelable(DefaultData.class.getClassLoader());
        sub_category=in.createTypedArrayList(DefaultData.CREATOR);
        country=in.readParcelable(DefaultData.class.getClassLoader());
        state=in.readParcelable(DefaultData.class.getClassLoader());
        desireToWork=in.readParcelable(DefaultData.class.getClassLoader());
        haveLicence=in.readParcelable(DefaultData.class.getClassLoader());
        gender=in.readParcelable(DefaultData.class.getClassLoader());
        nationality=in.readParcelable(DefaultData.class.getClassLoader());
        permanenceType=in.readParcelable(DefaultData.class.getClassLoader());
        availability=in.readParcelable(DefaultData.class.getClassLoader());
        email = in.readString();
        password = in.readString();
        countryId = in.readString();
        stateId = in.readString();
        nationalityId = in.readString();
        desireWork = in.readString();
        categoryId = in.readString();
        subCategoryId = in.readString();
        permanenceTypeId = in.readString();
        haveLicenceId = in.readString();
        genderId = in.readString();
        resumeType = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public static Creator<User> getCREATOR() {
        return CREATOR;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCommercialActivities() {
        return commercialActivities;
    }

    public void setCommercialActivities(String commercialActivities) {
        this.commercialActivities = commercialActivities;
    }

    public String getCommercialActivitiesNo() {
        return commercialActivitiesNo;
    }

    public void setCommercialActivitiesNo(String commercialActivitiesNo) {
        this.commercialActivitiesNo = commercialActivitiesNo;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStaticPhoneNumber() {
        return staticPhoneNumber;
    }

    public void setStaticPhoneNumber(String staticPhoneNumber) {
        this.staticPhoneNumber = staticPhoneNumber;
    }

    public String getHourlySalary() {
        return hourlySalary;
    }

    public void setHourlySalary(String hourlySalary) {
        this.hourlySalary = hourlySalary;
    }

    public String getSalaryPerDay() {
        return salaryPerDay;
    }

    public void setSalaryPerDay(String salaryPerDay) {
        this.salaryPerDay = salaryPerDay;
    }

    public String getSalaryPerMonth() {
        return salaryPerMonth;
    }

    public void setSalaryPerMonth(String salaryPerMonth) {
        this.salaryPerMonth = salaryPerMonth;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public DefaultData getCategory() {
        return category;
    }

    public void setCategory(DefaultData category) {
        this.category = category;
    }

    public List<DefaultData> getSub_category() {
        return sub_category;
    }

    public void setSub_category(List<DefaultData> sub_category) {
        this.sub_category = sub_category;
    }

    public DefaultData getCountry() {
        return country;
    }

    public void setCountry(DefaultData country) {
        this.country = country;
    }

    public DefaultData getState() {
        return state;
    }

    public void setState(DefaultData state) {
        this.state = state;
    }

    public DefaultData getDesireToWork() {
        return desireToWork;
    }

    public void setDesireToWork(DefaultData desireToWork) {
        this.desireToWork = desireToWork;
    }

    public DefaultData getHaveLicence() {
        return haveLicence;
    }

    public void setHaveLicence(DefaultData haveLicence) {
        this.haveLicence = haveLicence;
    }

    public DefaultData getAvailability() {
        return availability;
    }

    public void setAvailability(DefaultData availability) {
        this.availability = availability;
    }

    public DefaultData getGender() {
        return gender;
    }

    public void setGender(DefaultData gender) {
        this.gender = gender;
    }

    public DefaultData getNationality() {
        return nationality;
    }

    public void setNationality(DefaultData nationality) {
        this.nationality = nationality;
    }

    public DefaultData getPermanenceType() {
        return permanenceType;
    }

    public void setPermanenceType(DefaultData permanenceType) {
        this.permanenceType = permanenceType;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProfessionLicence() {
        return professionLicence;
    }

    public void setProfessionLicence(String professionLicence) {
        this.professionLicence = professionLicence;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getNationalityId() {
        return nationalityId;
    }

    public void setNationalityId(String nationalityId) {
        this.nationalityId = nationalityId;
    }

    public String getDesireWork() {
        return desireWork;
    }

    public void setDesireWork(String desireWork) {
        this.desireWork = desireWork;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getPermanenceTypeId() {
        return permanenceTypeId;
    }

    public void setPermanenceTypeId(String permanenceTypeId) {
        this.permanenceTypeId = permanenceTypeId;
    }

    public String getHaveLicenceId() {
        return haveLicenceId;
    }

    public void setHaveLicenceId(String haveLicenceId) {
        this.haveLicenceId = haveLicenceId;
    }

    public String getGenderId() {
        return genderId;
    }

    public void setGenderId(String genderId) {
        this.genderId = genderId;
    }

    public String getResumeType() {
        return resumeType;
    }

    public void setResumeType(String resumeType) {
        this.resumeType = resumeType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(status);
        parcel.writeString(amount);
        parcel.writeString(id);
        parcel.writeString(currency);
        parcel.writeString(userType);
        parcel.writeString(name);
        parcel.writeString(age);
        parcel.writeString(commercialActivities);
        parcel.writeString(commercialActivitiesNo);
        parcel.writeString(district);
        parcel.writeString(experience);
        parcel.writeString(job);
        parcel.writeString(latitude);
        parcel.writeString(longitude);
        parcel.writeString(phone);
        parcel.writeString(staticPhoneNumber);
        parcel.writeString(hourlySalary);
        parcel.writeString(salaryPerDay);
        parcel.writeString(salaryPerMonth);
        parcel.writeString(websiteUrl);
        parcel.writeString(image);
        parcel.writeString(professionLicence);
        parcel.writeString(resume);
        parcel.writeString(email);
        parcel.writeString(password);
        parcel.writeString(countryId);
        parcel.writeString(stateId);
        parcel.writeString(static_phone);
        parcel.writeString(website);
        parcel.writeString(nationalityId);
        parcel.writeString(desireWork);
        parcel.writeString(categoryId);
        parcel.writeString(subCategoryId);
        parcel.writeString(permanenceTypeId);
        parcel.writeString(haveLicenceId);
        parcel.writeString(genderId);
        parcel.writeParcelable(category, i);
        parcel.writeTypedList(sub_category);
        parcel.writeParcelable(country, i);
        parcel.writeParcelable(state, i);
        parcel.writeParcelable(nationality, i);
        parcel.writeParcelable(haveLicence, i);
        parcel.writeParcelable(availability, i);
        parcel.writeParcelable(permanenceType, i);
        parcel.writeParcelable(gender, i);
        parcel.writeParcelable(desireToWork, i);

    }
}
