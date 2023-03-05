from django.db import models

class Pacient(models.Model):
    first_name = models.CharField(max_length=40)
    last_name = models.CharField(max_length=40)
    gender = models.CharField(max_length=3)
    phone_number = models.CharField(max_length=10)
    registration_date = models.DateTimeField()
    disease_name = models.CharField(max_length=40)
    description = models.CharField(max_length=400)

    def __str__(self):
        return self.first_name + " " + self.last_name + " "+self.disease_name